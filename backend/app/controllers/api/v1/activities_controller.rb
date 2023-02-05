class Api::V1::ActivitiesController < Api::V1::BaseController
  before_action :set_activity, only: [:show, :update, :destroy]
  before_action -> { authorize_activity }, only: [:update, :show, :destroy]

  def index
    @activities = @user.activities
    @user.requests.create(method: :get, requestable_type: "Activity")
  end

  def show
    @user.requests.create(method: :get, requestable_type: "Activity")
  end

  def create
    @activity = @user.activities.build(activity_params)
    if @activity.save
      render :show, status: :created
    else
      render json: { message: @activity.errors.full_messages }, status: :unprocessable_entity
    end
    @user.requests.create(method: :post, requestable_type: "Activity")
  end

  def update
    if @activity.update(activity_params)
      render :show, status: :ok
      if @activity.end_d != nil
        begin  # "try" block
          @parsed = JSON.parse(`python3 lib/python/prueba.py #{@activity.id}`)
          @parsed.each do |measurement|
            Stress.create(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id)
          end
        rescue # optionally: `rescue Exception => ex`
          # Ignored
        end
      end
    else
      render json: { message: @activity.errors.full_messages }, status: :unprocessable_entity
    end
    @user.requests.create(method: :put, requestable_type: "Activity")
  end

  def destroy
    @activity.destroy
    render :show, status: :ok
    @user.requests.create(method: :delete, requestable_type: "Activity")
  end

  private

  def set_activity
    @activity = Activity.find(params[:id])
  end

  def authorize_activity
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def activity_params
    params.require(:activity).permit(:name, :start_d, :end_d, :viewed)
  end
end
