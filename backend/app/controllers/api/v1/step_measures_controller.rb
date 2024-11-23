class Api::V1::StepMeasuresController < Api::V1::BaseController
  before_action :set_step_measure, only: [:index, :create, :show, :update, :destroy]
  before_action :authorize_step_measure, only: [:show, :update, :destroy]

  def index
    @step_measures = @activity.step_measures
  end

  def show
  end

  def create
    @step_measure = StepMeasure.new(step_measure_params)
    if @step_measure.save
      render :show, status: :created
    else
      render json: { message: @step_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @step_measure.update(step_measure_params)
      render :show, status: :ok
    else
      render json: { message: @step_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @step_measure.destroy
    render :show, status: :ok
  end

  private

  def set_step_measure
    @step_measure = StepMeasure.find_by(activity_id: params[:activity_id])
  end

  def authorize_step_measure
    @activity = Activity.find_by(id: StepMeasure.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def step_measure_params
    params.require(:step_measure).permit(:measurement, :activity_id)
  end
end
