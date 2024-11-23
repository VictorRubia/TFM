class PersonMailer < ApplicationMailer
  def password_recovery
    @person = params[:person]

    attachments.inline["logo.png"] = File.read("#{Rails.root}/app/assets/images/logos/dibujo.png")
    mail(to: @person.email, subject: "¡Aquí tienes tu contraseña!")
  end

  def new_user
    @person = params[:person]

    attachments.inline["logo.png"] = File.read("#{Rails.root}/app/assets/images/logos/dibujo.png")
    mail(to: @person.email, subject: "Datos de su cuenta")
  end

  def edit_details
    @person = params[:person]

    attachments.inline["logo.png"] = File.read("#{Rails.root}/app/assets/images/logos/dibujo.png")
    mail(to: @person.email, subject: "Aquí están sus nuevos datos")
  end

end
