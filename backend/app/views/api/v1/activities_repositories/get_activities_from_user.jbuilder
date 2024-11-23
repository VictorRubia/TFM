json.array! @activity_assignations do |activity_assignation|
  json.id activity_assignation.id
  json.activity do
    json.id activity_assignation.activities_repository.id
    json.name activity_assignation.activities_repository.name
    json.name_wearos activity_assignation.activities_repository.name_wearos
    json.icon_url url_for(activity_assignation.activities_repository.icon) if activity_assignation.activities_repository.icon.attached?
  end
  json.tags do
    json.array! activity_assignation.tags_repositories do |tag|
      json.id tag.id
      json.type tag.tags_type
      json.name tag.name
      json.name_wearos tag.name_wearos
      json.icon_url url_for(tag.icon) if tag.icon.attached?
    end
  end
end
