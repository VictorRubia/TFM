class UserStress < ApplicationRecord
  belongs_to :user

  validates :level, presence: true,
            numericality: { greater_than_or_equal_to: 0, less_than_or_equal_to: 100 }

  before_save :round_level

  private

  def round_level
    self.level = level.round(2)
  end
end