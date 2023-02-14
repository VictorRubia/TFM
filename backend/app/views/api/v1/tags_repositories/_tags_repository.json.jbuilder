json.extract! tags_repository, :id, :name, :name_wearos, :created_at, :updated_at
json.image_url rails_blob_url(tags_repository.icon)
json.url api_v1_tags_repositories_url(tags_repository, format: :json)