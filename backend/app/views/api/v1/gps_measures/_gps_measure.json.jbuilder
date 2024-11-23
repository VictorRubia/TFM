json.extract! gps_measure, :id, :activity_id, :measurement, :created_at, :updated_at
json.url api_v1_gps_measure_url(gps_measure, format: :json)
