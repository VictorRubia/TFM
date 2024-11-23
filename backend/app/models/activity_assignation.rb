class ActivityAssignation < ApplicationRecord
  belongs_to :activities_repository
  belongs_to :user
  belongs_to :account
  has_many :activity_tags_assignations, through: :activities_repository
  has_many :tags_repositories, through: :activity_tags_assignations
end
