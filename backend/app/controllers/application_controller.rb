class ApplicationController < ActionController::Base

  def mid_process(activity)
    @activity = Activity.find(activity.id)
    @stress = Stress.where(activity_id: @activity.id)
    @ppg_measures = @activity.ppg_measures

    if @stress.empty? and !@ppg_measures.empty?
      @stress = []
      # @parsed = JSON.parse(`python3 lib/python/prueba.py #{@activity.id}`)
      # @parsed.each do |measurement|
      #   if @activity.end_d != nil
      #     Stress.create(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id)
      #     @stress.append(Stress.new(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id))
      #   else
      #     @stress.append(Stress.new(datetime: measurement["date"], level: measurement["measure"], activity_id: @activity.id))
      #   end
      # end
    end
    @stress
  end

  helper_method :mid_process

  private

  def authenticate
    rodauth.require_authentication # redirect to login page if not authenticated
  end

  def current_account
    rodauth.rails_account
  end

  helper_method :current_account

  def append_info_to_payload(payload)
    super
    payload["params"] = request.params
  end

end
