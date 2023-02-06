class ApplicationMailer < ActionMailer::Base
  default from: "#{ENV['MAIL_USR']}@gmail.com"
  layout 'mailer'
end
