class Activity < ApplicationRecord
  belongs_to :activities_repository
  belongs_to :user
  has_many :ppg_measures, dependent: :destroy
  has_many :gps_measures, dependent: :destroy
  has_many :accelerometer_measures, dependent: :destroy
  has_many :step_measures, dependent: :destroy
  has_many :significant_mov_measures, dependent: :destroy
  has_many :tags, dependent: :destroy
  has_many :stresses, dependent: :destroy
end
