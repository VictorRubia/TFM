class CreateSignificantMovMeasures < ActiveRecord::Migration[7.0]
  def change
    create_table :significant_mov_measures do |t|
      t.json :measurement
      t.references :activity, null: false, foreign_key: true

      t.timestamps
    end
  end
end
