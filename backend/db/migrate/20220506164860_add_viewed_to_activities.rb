class AddViewedToActivities < ActiveRecord::Migration[7.0]
  def change
    add_column :activities, :viewed, :boolean, :default => false
  end
end
