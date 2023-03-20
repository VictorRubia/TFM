class ActivityAssignation < ApplicationRecord
  belongs_to :activities_repository
  belongs_to :user
  belongs_to :account
end
