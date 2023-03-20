class CreateActivitiesRepositories < ActiveRecord::Migration[7.0]
  def change
    create_table :activities_repositories do |t|
      t.string :name
      t.string :name_wearos
      t.references :account, null: false, foreign_key: true

      t.timestamps
    end
  end
end
