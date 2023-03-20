class ActivityTagsAssignation < ApplicationRecord
  belongs_to :activities_repository
  belongs_to :tags_repository
  belongs_to :account
end
