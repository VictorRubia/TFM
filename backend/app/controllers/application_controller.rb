require 'net/http'
require 'uri'
require 'csv'

class ApplicationController < ActionController::Base

  # def mid_process(activity)
  #   @activity = Activity.find(activity.id)
  #   @stress = Stress.where(activity_id: @activity.id)
  #   @ppg_measures = @activity.ppg_measures
  #
  #   if @stress.empty? && !@ppg_measures.empty?
  #     @stress = []
  #
  #     # Obtener medidas PPG
  #     ppg_measures = @ppg_measures.map do |medida|
  #       JSON.parse(medida['measurement']).map { |m| [m['timestamp'], m['measure']] }
  #     end.flatten(1)
  #
  #     # Generar el CSV en formato de texto
  #     csv_data = CSV.generate(headers: true) do |csv|
  #       csv << ['timestamp', 'measure']
  #       ppg_measures.each { |row| csv << row }
  #     end
  #
  #     # Hacer el POST request
  #     uri = URI.parse('http://20.199.23.23:8080/function/predict-model')
  #     http = Net::HTTP.new(uri.host, uri.port)
  #     request = Net::HTTP::Post.new(uri.path, { 'Content-Type' => 'text/csv' })
  #     request.body = csv_data
  #
  #     response = http.request(request)
  #
  #     if response.code.to_i == 200
  #       @parsed = JSON.parse(response.body)
  #
  #       @parsed.each do |measurement|
  #         stress_record = Stress.new(
  #           datetime: measurement["date"],
  #           level: measurement["measure"],
  #           activity_id: @activity.id
  #         )
  #
  #         if @activity.end_d
  #           stress_record.save
  #         end
  #
  #         @stress << stress_record
  #       end
  #     else
  #       raise "Error al procesar las medidas: #{response.message}"
  #     end
  #   end
  #
  #   @stress
  # end

  def mid_process(activity)
    @activity = Activity.find(activity.id)
    @stress = Stress.where(activity_id: @activity.id)
    @ppg_measures = @activity.ppg_measures

    if @stress.empty? && !@ppg_measures.empty?
      @stress = []

      # Obtener medidas PPG
      ppg_measures = @ppg_measures.map do |medida|
        JSON.parse(medida['measurement']).map { |m| [m['measure'], m['timestamp']] }
      end.flatten(1)

      # Generar el CSV en formato de texto
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

        @parsed.each do |measurement|
          stress_record = Stress.new(
            datetime: measurement["date"],
            level: measurement["measure"],
            activity_id: @activity.id
          )

          stress_record.save if @activity.end_d

          @stress << stress_record
        end
      else
        raise "Error al procesar las medidas: #{response.message}"
      end

      # Lógica adicional del segundo endpoint (DASS)
      begin
        # Generar el CSV nuevamente
        csv_data_dass = CSV.generate(headers: true) do |csv|
          csv << %w[ppg timestamp]
          ppg_measures.each { |row| csv << row.reverse } # Invertir el orden para cumplir con el formato DASS
        end

        # POST request al segundo endpoint
        uri_dass = URI.parse('http://20.199.23.23:8080/function/predict-model-dass')
        request_dass = Net::HTTP::Post.new(uri_dass.path, { 'Content-Type' => 'text/csv' })
        request_dass.body = csv_data_dass

        response_dass = http.request(request_dass)

        if response_dass.code.to_i == 200
          parsed_dass = JSON.parse(response_dass.body)

          if @activity.user && parsed_dass["probabilidad"]
            user_stress = @activity.user.user_stress || @activity.user.build_user_stress
            user_stress.level = parsed_dass["probabilidad"]

            unless user_stress.save
              Rails.logger.error("Error al guardar los datos de estrés del usuario: #{user_stress.errors.full_messages.join(', ')}")
              raise "No fue posible actualizar los niveles de estrés del usuario."
            end
          end
        else
          Rails.logger.error("Error al comunicarse con el servidor DASS: #{response_dass.message}")
          raise "Error al procesar los niveles de estrés del modelo DASS."
        end
      rescue JSON::ParserError => e
        Rails.logger.error("Error al procesar datos JSON del modelo DASS: #{e.message}")
        raise "Error al procesar los datos del modelo DASS."
      rescue Net::HTTPError => e
        Rails.logger.error("Error al realizar la solicitud HTTP al modelo DASS: #{e.message}")
        raise "No fue posible comunicarse con el servidor DASS."
      rescue StandardError => e
        Rails.logger.error("Error general en el proceso del modelo DASS: #{e.message}")
        raise "Ocurrió un error inesperado al procesar los niveles de estrés DASS: #{e.message}."
      end
    end

    @stress
  end

  helper_method :mid_process

  private

  def authenticate
    rodauth.require_authentication # redirect to login page if not authenticated
  end

  def current_account
    rodauth.rails_account
  end

  helper_method :current_account

  def append_info_to_payload(payload)
    super
    payload["params"] = request.params
  end

end
