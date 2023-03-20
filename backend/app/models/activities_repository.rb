class ActivitiesRepository < ApplicationRecord
  belongs_to :account
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