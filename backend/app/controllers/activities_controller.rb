require 'csv'
require 'time'
require 'json'
require 'net/http'
require 'uri'

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
    @final.sort_by!{ |hsh| hsh[:timestamp] }

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

    @csv_data = CSV.generate(headers: true) do |csv|
      csv << ['ppg', 'timestamp']  # Encabezados del archivo CSV

      @measures.each do |medidas|
        obj = JSON.parse(medidas['measurement'])
        obj.each do |m|
          csv << [m['measure'], m['timestamp']]  # Agregar cada fila al CSV
        end
      end
    end

    # Renderizar el CSV como respuesta
    respond_to do |format|
      format.csv { send_data @csv_data, filename: "ppg_measures.csv" }
    end
  end

  def accelerometer_measures
    @measures = @activity.accelerometer_measures

    @json = []
    @measures.each do |medidas|
      obj = JSON.parse(medidas['measurement'])
      obj.each do |m|
        @json.append({ppg: m['measure'], timestamp: m['timestamp']})
      end
    end

    render :json => @json.to_json

  end

  # def reprocess
  #   @activity = Activity.find(params[:id_activity])
  #
  #   # Obtener medidas PPG
  #   ppg_measures = @activity.ppg_measures.map do |medida|
  #     JSON.parse(medida['measurement']).map { |m| [m['timestamp'], m['measure']] }
  #   end.flatten(1)
  #
  #   # Generar el CSV en formato de texto
  #   csv_data = CSV.generate(headers: true) do |csv|
  #     csv << ['timestamp', 'measure']
  #     ppg_measures.each { |row| csv << row }
  #   end
  #
  #   # Hacer el POST request
  #   uri = URI.parse('http://20.199.23.23:8080/function/predict-model')
  #   http = Net::HTTP.new(uri.host, uri.port)
  #   request = Net::HTTP::Post.new(uri.path, { 'Content-Type' => 'text/csv' })
  #   request.body = csv_data
  #
  #   response = http.request(request)
  #
  #   if response.code.to_i == 200
  #     @parsed = JSON.parse(response.body)
  #     @activity.stresses.destroy_all
  #
  #     @parsed.each do |measurement|
  #       Stress.create(
  #         datetime: measurement["date"],
  #         level: measurement["measure"],
  #         activity_id: @activity.id
  #       )
  #     end
  #
  #     redirect_to dashboard_activity_details_url, notice: "Actividad actualizada."
  #   else
  #     redirect_to dashboard_activity_details_url, alert: "Error al reprocesar la actividad: #{response.message}."
  #   end
  # end

  # def reprocess
  #   @activity = Activity.find(params[:id_activity])
  #   Rails.logger.info("Actividad encontrada: #{@activity.id}")
  #
  #   begin
  #     # Procesar medidas PPG y niveles de estrés
  #     stress_records = mid_process(@activity)
  #     Rails.logger.info("Registros de estrés procesados: #{stress_records}")
  #
  #     if stress_records.empty?
  #     Rails.logger.warn("No se encontraron datos suficientes para procesar la actividad.")
  #     redirect_to dashboard_activity_details_url, alert: "No se pudo procesar la actividad porque los datos son insuficientes. Intente nuevamente más tarde."
  #     return
  #   end
  #
  #   # Generar el CSV nuevamente
  #   ppg_measures = @activity.ppg_measures.map do |medida|
  #     JSON.parse(medida['measurement']).map { |m| [m['measure'], m['timestamp']] }
  #   end.flatten(1)
  #   Rails.logger.info("Medidas PPG procesadas exitosamente: #{ppg_measures.size}")
  #
  #   csv_data = CSV.generate(headers: true) do |csv|
  #     csv << %w[ppg timestamp]
  #     ppg_measures.each { |row| csv << row }
  #   end
  #   Rails.logger.info("Archivo CSV generado correctamente.")
  #
  #   # POST request al segundo endpoint
  #   uri_dass = URI.parse('http://20.199.23.23:8080/function/predict-model-dass')
  #   http = Net::HTTP.new(uri_dass.host, uri_dass.port)
  #   request_dass = Net::HTTP::Post.new(uri_dass.path, { 'Content-Type' => 'text/csv' })
  #   request_dass.body = csv_data
  #
  #   response_dass = http.request(request_dass)
  #   Rails.logger.info("Respuesta del servidor DASS: Código #{response_dass.code}, Respuesta: #{response_dass.body}")
  #
  #   if response_dass.code.to_i == 200
  #     parsed_dass = JSON.parse(response_dass.body)
  #     Rails.logger.info("Respuesta procesada correctamente: #{parsed_dass}")
  #
  #     if @activity.user && parsed_dass["probabilidad"]
  #       user_stress = @activity.user.user_stress || @activity.user.build_user_stress
  #       user_stress.level = parsed_dass["probabilidad"]
  #
  #       if user_stress.save
  #         Rails.logger.info("El nivel de estrés del usuario #{@activity.user.id} se actualizó correctamente con un nivel de #{user_stress.level}.")
  #       else
  #         Rails.logger.error("Error al guardar los datos de estrés del usuario: #{user_stress.errors.full_messages.join(', ')}")
  #         redirect_to dashboard_activity_details_url, alert: "No fue posible actualizar los niveles de estrés del usuario. Por favor, intente más tarde."
  #         return
  #       end
  #     end
  #   else
  #     Rails.logger.error("Error al comunicarse con el servidor DASS: #{response_dass.message}")
  #     redirect_to dashboard_activity_details_url, alert: "Hubo un problema al reprocesar los niveles de estrés. Intente más tarde."
  #     return
  #   end
  #
  #   redirect_to dashboard_activity_details_url, notice: "La actividad se actualizó exitosamente."
  # rescue JSON::ParserError => e
  #   Rails.logger.error("Error al procesar datos JSON: #{e.message}")
  #   redirect_to dashboard_activity_details_url, alert: "Ocurrió un problema al procesar los datos. Asegúrese de que el formato de los datos sea correcto."
  # rescue Net::HTTPError => e
  #   Rails.logger.error("Error al realizar la solicitud HTTP: #{e.message}")
  #   redirect_to dashboard_activity_details_url, alert: "No fue posible comunicarse con el servidor para actualizar los datos. Por favor, intente más tarde."
  # rescue StandardError => e
  #   Rails.logger.error("Error general en el proceso: #{e.message}")
  #   Rails.logger.error(e.backtrace.join("\n"))
  #   redirect_to dashboard_activity_details_url, alert: "Ocurrió un error inesperado: #{e.message}. Por favor, contacte al soporte."
  #   end
  # end

  def reprocess
    @activity = Activity.find(params[:id_activity])
    Rails.logger.info("Actividad encontrada: #{@activity.id}")

    begin
      # Procesar las medidas PPG para el primer endpoint
      ppg_measures = @activity.ppg_measures.map do |medida|
        JSON.parse(medida['measurement']).map { |m| [m['measure'], m['timestamp']] }
      end.flatten(1)

      # Generar el CSV en formato de texto para el primer endpoint
      csv_data = CSV.generate(headers: true) do |csv|
        csv << ['ppg', 'timestamp']
        ppg_measures.each { |row| csv << row }
      end

      # Hacer el POST request al primer endpoint
      uri = URI.parse('http://20.199.23.23:8080/function/predict-model')
      http = Net::HTTP.new(uri.host, uri.port)
      request = Net::HTTP::Post.new(uri.path, { 'Content-Type' => 'text/csv' })
      request.body = csv_data

      response = http.request(request)

      if response.code.to_i == 200
        @parsed = JSON.parse(response.body)
        Rails.logger.info("Respuesta procesada correctamente del primer endpoint: #{@parsed}")

        @activity.stresses.destroy_all

        @parsed.each do |measurement|
          Stress.create(
            datetime: measurement["date"],
            level: measurement["measure"],
            activity_id: @activity.id
          )
        end
      else
        Rails.logger.error("Error al reprocesar la actividad con el primer endpoint: Código #{response.code}, Mensaje: #{response.message}")
        redirect_to dashboard_activity_details_url, alert: "Error al reprocesar la actividad: #{response.code} - #{response.message}. Por favor, intente nuevamente más tarde o contacte al soporte."
        return
      end

      # Generar el CSV nuevamente para el segundo endpoint
      ppg_measures = @activity.ppg_measures.map do |medida|
        JSON.parse(medida['measurement']).map { |m| [m['measure'], m['timestamp']] }
      end.flatten(1)
      Rails.logger.info("Medidas PPG procesadas exitosamente: #{ppg_measures.size}")

      csv_data = CSV.generate(headers: true) do |csv|
        csv << %w[ppg timestamp]
        ppg_measures.each { |row| csv << row }
      end
      Rails.logger.info("Archivo CSV generado correctamente para el segundo endpoint.")

      # POST request al segundo endpoint
      uri_dass = URI.parse('http://20.199.23.23:8080/function/predict-model-dass')
      http = Net::HTTP.new(uri_dass.host, uri_dass.port)
      request_dass = Net::HTTP::Post.new(uri_dass.path, { 'Content-Type' => 'text/csv' })
      request_dass.body = csv_data

      response_dass = http.request(request_dass)
      Rails.logger.info("Respuesta del servidor DASS: Código #{response_dass.code}, Respuesta: #{response_dass.body}")

      if response_dass.code.to_i == 200
        parsed_dass = JSON.parse(response_dass.body)
        Rails.logger.info("Respuesta procesada correctamente del segundo endpoint: #{parsed_dass}")

        if @activity.user && parsed_dass["probabilidad"]
          user_stress = @activity.user.user_stress || @activity.user.build_user_stress
          user_stress.level = parsed_dass["probabilidad"]

          if user_stress.save
            Rails.logger.info("El nivel de estrés del usuario #{@activity.user.id} se actualizó correctamente con un nivel de #{user_stress.level}.")
          else
            Rails.logger.error("Error al guardar los datos de estrés del usuario: #{user_stress.errors.full_messages.join(', ')}")
            redirect_to dashboard_activity_details_url, notice: "La actividad se actualizó exitosamente."
            return
          end
        end
      else
        Rails.logger.error("Error al comunicarse con el servidor DASS: Código #{response_dass.code}, Mensaje: #{response_dass.message}, Respuesta: #{response_dass.body}")
        # redirect_to dashboard_activity_details_url, alert: "Error al reprocesar niveles de estrés: #{response_dass.code} - #{response_dass.message}. Detalles: #{response_dass.body}. Contacte al soporte si el problema persiste."
        redirect_to dashboard_activity_details_url, notice: "La actividad se actualizó exitosamente."
        return
      end

      redirect_to dashboard_activity_details_url, notice: "La actividad se actualizó exitosamente."
    rescue JSON::ParserError => e
      Rails.logger.error("Error al procesar datos JSON: #{e.message}")
      redirect_to dashboard_activity_details_url, alert: "Error al procesar la respuesta del servidor: #{e.message}. Asegúrese de que el formato de los datos sea correcto."
    rescue Net::HTTPError => e
      Rails.logger.error("Error al realizar la solicitud HTTP: #{e.message}")
      redirect_to dashboard_activity_details_url, alert: "Error de comunicación con el servidor: #{e.message}. Por favor, intente más tarde."
    rescue StandardError => e
      Rails.logger.error("Error general en el proceso: #{e.message}")
      Rails.logger.error(e.backtrace.join("\n"))
      redirect_to dashboard_activity_details_url, alert: "Error inesperado: #{e.message}. Contacte al soporte técnico."
    end
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
      date = Time.at(arr1['timestamp'] / 1000.0).strftime('%d/%m/%Y %H:%M:%S.%L')
      hash[date][0][:latitude] = arr1['latitude']
      hash[date][0][:longitude] = arr1['longitude']
    end

    array2.each do |arr2|
      date = Time.at(arr2['timestamp'] / 1000.0).strftime('%d/%m/%Y %H:%M:%S.%L')
      hash[date][1][:measure] = arr2['measure']
    end

    array3.each do |arr3|
      date = Time.at(arr3['timestamp'] / 1000.0).strftime('%d/%m/%Y %H:%M:%S.%L')
      hash[date][2][:ejeX] = arr3['ejeX']
      hash[date][2][:ejeY] = arr3['ejeY']
      hash[date][2][:ejeZ] = arr3['ejeZ']
    end

    array4.each do |arr4|
      date = Time.at(arr4['timestamp'] / 1000.0).strftime('%d/%m/%Y %H:%M:%S.%L')
      hash[date][3][:count] = arr4['count']
    end

    array5.each do |arr5|
      date = Time.at(arr5['timestamp'] / 1000.0).strftime('%d/%m/%Y %H:%M:%S.%L')
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
