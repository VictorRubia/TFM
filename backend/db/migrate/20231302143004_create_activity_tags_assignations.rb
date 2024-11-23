class CreateActivityTagsAssignations < ActiveRecord::Migration[7.0]
  def change
    create_table :activity_tags_assignations do |t|
      t.references :activities_repository, null: false, foreign_key: true
      t.references :tags_repository, null: false, foreign_key: true
      t.references :account, null: false, foreign_key: true

      t.timestamps
    end
  end
end
