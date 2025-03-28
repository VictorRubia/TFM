class ActivitiesRepository < ApplicationRecord
  belongs_to :account
  has_many :activities
  has_many :activity_assignations
  has_many :activity_tags_assignations
  has_many :tags_repositories, through: :activity_tags_assignations
  has_one_attached :icon

  def self.search(search)
    if search
      tag = ActivitiesRepository.where("name like ?", "%#{search}%")
      if tag
        self.where(id: tag)
      else
        ActivitiesRepository.all
      end
    else
      ActivitiesRepository.all
    end
  end
end