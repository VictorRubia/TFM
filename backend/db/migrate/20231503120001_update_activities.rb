class UpdateActivities < ActiveRecord::Migration[7.0]
  def change
    remove_column :activities, :name, :string
    add_reference :activities, :activities_repository, null: false, foreign_key: true
  end
end
