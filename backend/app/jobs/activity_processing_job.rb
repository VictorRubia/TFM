class ActivityProcessingJob < ApplicationJob
  queue_as :default

  after_perform do |job|

  end

  def perform(*args)
    # Do something later
  end
end
