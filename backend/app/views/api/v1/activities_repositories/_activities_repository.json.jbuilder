json.extract! activities_repository, :id, :name, :name_wearos
json.image_url rails_blob_url(activities_repository.icon)