class ChangeActivityAssignations < ActiveRecord::Migration[7.0]
  def change
    remove_column :activity_assignations, :tags_repository_id, null: false, foreign_key: true
  end
end
