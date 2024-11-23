class TagsRepository < ApplicationRecord
  has_many :activity_tags_assignations
  has_many :activities_repositories, through: :activity_tags_assignations
  has_one_attached :icon
  def self.search(search)
    if search
      tag = TagsRepository.where("name like ?", "%#{search}%")
      if tag
        self.where(id: tag)
      else
        TagsRepository.all
      end
    else
      TagsRepository.all
    end
  end
end