json.extract! activity, :id, :user_id, :name, :start_d, :end_d, :created_at, :updated_at
json.url api_v1_activity_url(activity, format: :json)
