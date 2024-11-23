class StressesController < ApplicationController
  before_action :set_stress, only: %i[ show edit update destroy ]

  # GET /stresses or /stresses.json
  def index
    @stresses = Stress.all
  end

  # GET /stresses/1 or /stresses/1.json
  def show
  end

  # GET /stresses/new
  def new
    @stress = Stress.new
  end

  # GET /stresses/1/edit
  def edit
  end

  # POST /stresses or /stresses.json
  def create
    @stress = Stress.new(stress_params)

    respond_to do |format|
      if @stress.save
        format.html { redirect_to stress_url(@stress), notice: "Stress was successfully created." }
        format.json { render :show, status: :created, location: @stress }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @stress.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /stresses/1 or /stresses/1.json
  def update
    respond_to do |format|
      if @stress.update(stress_params)
        format.html { redirect_to stress_url(@stress), notice: "Stress was successfully updated." }
        format.json { render :show, status: :ok, location: @stress }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @stress.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /stresses/1 or /stresses/1.json
  def destroy
    @stress.destroy

    respond_to do |format|
      format.html { redirect_to stresses_url, notice: "Stress was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_stress
      @stress = Stress.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def stress_params
      params.require(:stress).permit(:datetime, :level, :activity_id)
    end
end
