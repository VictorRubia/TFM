json.extract! tags_repository, :id, :name, :name_wearos, :tags_type
json.image_url rails_blob_url(tags_repository.icon)