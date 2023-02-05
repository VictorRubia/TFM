require 'csv'
require 'time'
require 'json'

class ActivitiesController < ApplicationController
  before_action :set_activity, only: %i[ show edit update destroy ppg_measures accelerometer_measures ]

  # GET /activities or /activities.json
  def index
    @activities = Activity.all
  end

  # GET /activities/1 or /activities/1.json
  def show
  end

  # GET /activities/new
  def new
    @activity = Activity.new
  end

  # GET /activities/1/edit
  def edit
  end

  # POST /activities or /activities.json
  def create
    @activity = Activity.new(activity_params)

    respond_to do |format|
      if @activity.save
        format.html { redirect_to activity_url(@activity), notice: "Activity was successfully created." }
        format.json { render :show, status: :created, location: @activity }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /activities/1 or /activities/1.json
  def update
    respond_to do |format|
      if @activity.update(activity_params)
        format.html { redirect_to activity_url(@activity), notice: "Activity was successfully updated." }
        format.json { render :show, status: :ok, location: @activity }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @activity.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /activities/1 or /activities/1.json
  def destroy
    # @ppg_measures = PpgMeasure.find_by id:
    user_id = @activity.user_id
    @activity.destroy

    respond_to do |format|
      # format.html { redirect_to activities_url, notice: "Activity was successfully destroyed." }
      format.html { redirect_to dashboard_view_activity_url, params: { id: user_id}, notice: "Actividad eliminada." }
      format.json { head :no_content }
    end
  end

  def export
    @activity = Activity.find(params[:id])

    @ppg_measures = @activity.ppg_measures
    @accelerometer_measures = @activity.accelerometer_measures
    @gps_measures = @activity.gps_measures
    @step_measures = @activity.step_measures
    @significant_mov_measures = @activity.significant_mov_measures

    master1 = @ppg_measures.map { |f| JSON.parse(f.measurement) }.flatten
    master2 = @gps_measures.map { |f| JSON.parse(f.measurement) }.flatten
    master3 = @accelerometer_measures.map { |f| JSON.parse(f.measurement) }.flatten
    master4 = @step_measures.map { |f| JSON.parse(f.measurement) }.flatten
    master5 = @significant_mov_measures.map { |f| JSON.parse(f.measurement) }.flatten

    @final = merge_arrays(master2, master1, master3, master4, master5)
    puts @final
    @final.sort_by!{ |hsh| hsh[:date] }

    respond_to do |format|
      format.csv do
        response.headers['Content-Type'] = 'text/csv'
        response.headers['Content-Disposition'] = "attachment; filename=export_" + @activity.id.to_s + ".csv"
        render template: "activities/export"
      end
    end
  end

  def ppg_measures
    @measures = @activity.ppg_measures

    @json = []
    @measures.each do |medidas|
      obj = JSON.parse(medidas['measurement'])
      obj.each do |m|
        @json.append({ppg: m['measure'], timer: m['date']})
      end
    end

    render :json => @json.to_json

  end

  def accelerometer_measures
    @measures = @activity.accelerometer_measures

    @json = []
    @measures.each do |medidas|
      obj = JSON.parse(medidas['measurement'])
      obj.each do |m|
        @json.append({ppg: m['measure'], timer: m['date']})
      end
    end

    render :json => @json.to_json

  end

  def reprocess
    @activity = Activity.find(params[:id_activity])
    @parsed = JSON.parse(`python3 lib/python/prueba.py #{@activity.id}`)
    @activity.stresses.destroy_all
    @parsed.each do |measurement|
      Stress.create(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id)
    end
    redirect_to dashboard_activity_details_url, notice: "Actividad actualizado."
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_activity
      @activity = Activity.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def activity_params
      params.require(:activity).permit(:name, :start_d, :end_d, :user_id, :id_activity)
    end

    def merge_arrays(array1, array2, array3, array4, array5)
      hash = Hash.new { |h, k| h[k] = [{ latitude: 0, longitude: 0 }, { measure: 0 }, {ejeX: 0, ejeY: 0, ejeZ: 0}, {count: 0}, {significant_mov: 0}] }

      array1.each do |arr1|
        date = Time.parse(arr1['date']).strftime('%d/%m/%Y %H:%M:%S.%L')
        hash[date][0][:latitude] = arr1['latitude']
        hash[date][0][:longitude] = arr1['longitude']
      end

      array2.each do |arr2|
        date = Time.parse(arr2['date']).strftime('%d/%m/%Y %H:%M:%S.%L')
        hash[date][1][:measure] = arr2['measure']
      end

      array3.each do |arr3|
        date = Time.parse(arr3['date']).strftime('%d/%m/%Y %H:%M:%S.%L')
        hash[date][2][:ejeX] = arr3['ejeX']
        hash[date][2][:ejeY] = arr3['ejeY']
        hash[date][2][:ejeZ] = arr3['ejeZ']
      end

      array4.each do |arr4|
        date = Time.parse(arr4['date']).strftime('%d/%m/%Y %H:%M:%S.%L')
        hash[date][3][:count] = arr4['count']
      end

      array5.each do |arr5|
        date = Time.parse(arr5['date']).strftime('%d/%m/%Y %H:%M:%S.%L')
        hash[date][4][:significant_mov] = arr5['trigger']
      end

      result = []
      hash.each do |key, value|
        result << { date: key, latitude: value[0][:latitude],
                    longitude: value[0][:longitude],
                    measure: value[1][:measure],
                    ejeX: value[2][:ejeX],
                    ejeY: value[2][:ejeY],
                    ejeZ: value[2][:ejeZ],
                    count: value[3][:count],
                    significant_mov: value[4][:significant_mov] }
      end

      result
    end
end
