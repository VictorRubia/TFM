class GpsMeasuresController < ApplicationController
  before_action :set_gps_measure, only: %i[ show edit update destroy ]

  # GET /gps_measures or /gps_measures.json
  def index
    @gps_measures = GpsMeasure.all
  end

  # GET /gps_measures/1 or /gps_measures/1.json
  def show
  end

  # GET /gps_measures/new
  def new
    @gps_measure = GpsMeasure.new
  end

  # GET /gps_measures/1/edit
  def edit
  end

  # POST /gps_measures or /gps_measures.json
  def create
    @gps_measure = GpsMeasure.new(gps_measure_params)

    respond_to do |format|
      if @gps_measure.save
        format.html { redirect_to gps_measure_url(@gps_measure), notice: "Gps measure was successfully created." }
        format.json { render :show, status: :created, location: @gps_measure }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @gps_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /gps_measures/1 or /gps_measures/1.json
  def update
    respond_to do |format|
      if @gps_measure.update(gps_measure_params)
        format.html { redirect_to gps_measure_url(@gps_measure), notice: "Gps measure was successfully updated." }
        format.json { render :show, status: :ok, location: @gps_measure }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @gps_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /gps_measures/1 or /gps_measures/1.json
  def destroy
    @gps_measure.destroy

    respond_to do |format|
      format.html { redirect_to gps_measures_url, notice: "Gps measure was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_gps_measure
      @gps_measure = GpsMeasure.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def gps_measure_params
      params.require(:gps_measure).permit(:measurement, :activity_id)
    end
end
