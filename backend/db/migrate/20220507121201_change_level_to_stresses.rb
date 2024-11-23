class ChangeLevelToStresses < ActiveRecord::Migration[7.0]
  def change
    change_column :stresses, :level, :integer
  end
end
