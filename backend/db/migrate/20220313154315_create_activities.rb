class CreateActivities < ActiveRecord::Migration[7.0]
  def change
    create_table :activities do |t|
      t.string :name
      t.datetime :start_d
      t.datetime :end_d
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end