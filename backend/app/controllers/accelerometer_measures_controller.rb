class AccelerometerMeasuresController < ApplicationController
  before_action :set_accelerometer_measure, only: %i[ show edit update destroy ]

  # GET /accelerometer_measures or /accelerometer_measures.json
  def index
    @accelerometer_measures = AccelerometerMeasure.all
  end

  # GET /accelerometer_measures/1 or /accelerometer_measures/1.json
  def show
  end

  # GET /accelerometer_measures/new
  def new
    @accelerometer_measure = AccelerometerMeasure.new
  end

  # GET /accelerometer_measures/1/edit
  def edit
  end

  # POST /accelerometer_measures or /accelerometer_measures.json
  def create
    @accelerometer_measure = AccelerometerMeasure.new(accelerometer_measure_params)

    respond_to do |format|
      if @accelerometer_measure.save
        format.html { redirect_to accelerometer_measure_url(@accelerometer_measure), notice: "Accelerometer measure was successfully created." }
        format.json { render :show, status: :created, location: @accelerometer_measure }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @accelerometer_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /accelerometer_measures/1 or /accelerometer_measures/1.json
  def update
    respond_to do |format|
      if @accelerometer_measure.update(accelerometer_measure_params)
        format.html { redirect_to accelerometer_measure_url(@accelerometer_measure), notice: "Accelerometer measure was successfully updated." }
        format.json { render :show, status: :ok, location: @accelerometer_measure }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @accelerometer_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /accelerometer_measures/1 or /accelerometer_measures/1.json
  def destroy
    @accelerometer_measure.destroy

    respond_to do |format|
      format.html { redirect_to accelerometer_measures_url, notice: "Accelerometer measure was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_accelerometer_measure
      @accelerometer_measure = AccelerometerMeasure.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def accelerometer_measure_params
      params.require(:accelerometer_measure).permit(:measurement, :activity_id)
    end
end
