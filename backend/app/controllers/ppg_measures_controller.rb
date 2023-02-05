class PpgMeasuresController < ApplicationController
  before_action :set_ppg_measure, only: %i[ show edit update destroy ]

  # GET /ppg_measures or /ppg_measures.json
  def index
    @ppg_measures = PpgMeasure.all
  end

  # GET /ppg_measures/1 or /ppg_measures/1.json
  def show
  end

  # GET /ppg_measures/new
  def new
    @ppg_measure = PpgMeasure.new
  end

  # GET /ppg_measures/1/edit
  def edit
  end

  # POST /ppg_measures or /ppg_measures.json
  def create
    @ppg_measure = PpgMeasure.new(ppg_measure_params)

    respond_to do |format|
      if @ppg_measure.save
        format.html { redirect_to ppg_measure_url(@ppg_measure), notice: "Ppg measure was successfully created." }
        format.json { render :show, status: :created, location: @ppg_measure }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @ppg_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /ppg_measures/1 or /ppg_measures/1.json
  def update
    respond_to do |format|
      if @ppg_measure.update(ppg_measure_params)
        format.html { redirect_to ppg_measure_url(@ppg_measure), notice: "Ppg measure was successfully updated." }
        format.json { render :show, status: :ok, location: @ppg_measure }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @ppg_measure.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /ppg_measures/1 or /ppg_measures/1.json
  def destroy
    @ppg_measure.destroy

    respond_to do |format|
      format.html { redirect_to ppg_measures_url, notice: "Ppg measure was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_ppg_measure
      @ppg_measure = PpgMeasure.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def ppg_measure_params
      params.require(:ppg_measure).permit(:measurement, :activity_id)
    end
end
