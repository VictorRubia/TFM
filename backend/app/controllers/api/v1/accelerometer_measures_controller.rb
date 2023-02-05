class Api::V1::AccelerometerMeasuresController < Api::V1::BaseController
  before_action :set_accelerometer_measure, only: [:index, :create, :show, :update, :destroy]
  before_action :authorize_accelerometer_measure, only: [:show, :update, :destroy]

  def index
    @accelerometer_measures = @activity.accelerometer_measures
  end

  def show
  end

  def create
    @accelerometer_measure = AccelerometerMeasure.new(accelerometer_measure_params)
    if @accelerometer_measure.save
      render :show, status: :created
    else
      render json: { message: @accelerometer_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @accelerometer_measure.update(accelerometer_measure_params)
      render :show, status: :ok
    else
      render json: { message: @accelerometer_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @accelerometer_measure.destroy
    render :show, status: :ok
  end

  private

  def set_accelerometer_measure
    @accelerometer_measure = AccelerometerMeasure.find_by(activity_id: params[:activity_id])
  end

  def authorize_accelerometer_measure
    @activity = Activity.find_by(id: AccelerometerMeasure.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def accelerometer_measure_params
    params.require(:accelerometer_measure).permit(:measurement, :activity_id)
  end
end
