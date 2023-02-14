import { Application } from "@hotwired/stimulus"
import * as ActiveStorage from "@rails/activestorage"
import Uppy from '@uppy/core'
import Dashboard from "@uppy/dashboard"
import ActiveStorageUpload from "@puglet5/uppy-activestorage-upload"
import ImageEditor from '@uppy/image-editor'
import Spanish from '@uppy/locales/lib/es_ES'
import Webcam from '@uppy/webcam'

const application = Application.start()
ActiveStorage.start()

document.addEventListener('turbo:load', () => {
    document.querySelectorAll('[data-uppy]').forEach(element => setupUppy(element))
})

function setupUppy(element) {
    let trigger = element.querySelector('[data-behavior="uppy-trigger"]')
    let direct_upload_url = document.querySelector("meta[name='direct-upload-url']").getAttribute("content")
    let cancel = document.querySelectorAll(".close-tags-modal")
    let field_name = element.dataset.uppy
    const companionUrl = 'http://tfm-uppy-companion.herokuapp.com/'

    trigger.addEventListener("click", (event) => event.preventDefault())

    const uppy = new Uppy({
        allowMultipleUploads: false,
        autoProceed: true,
        allowMultipleUploadBatches: false,
        locale: Spanish,
        restrictions: {
            maxFileSize: 5000000,
            maxNumberOfFiles: 1,
            minNumberOfFiles: 1,
            allowedFileTypes: ['image/*', '.jpg', '.jpeg', '.png'],
        },
    })

    cancel.forEach(button => {
        button.addEventListener('click', () => {
            uppy.cancelAll();
        });
    });

    uppy.use(ActiveStorageUpload, {
        directUploadUrl: direct_upload_url
    })

    uppy.use(Webcam,{
        modes: ['picture'],
    })


    uppy.use(Dashboard, {
        trigger: trigger,
        target: '.uppy-picker',
        plugins: ['ImageEditor', 'Webcam'],
        width: 800,
        height: 500,
        thumbnailWidth: 280,
        proudlyDisplayPoweredByUppy: false,
        autoOpenFileEditor: true,
        inline: true,
        doneButtonHandler: null,
    })

    uppy.use(ImageEditor, {
        target: Dashboard,
        quality: 0.8,
        // companionUrl:
        cropperOptions: {
            initialAspectRatio: 1/1,
            aspectRatio: 1/1,
            croppedCanvasOptions: {
                fillColor: '#fff',
            },
        }
    })

    uppy.on('complete', (result) => {
        // Rails.ajax
        // or show a preview:
        element.querySelectorAll('[data-pending-upload]').forEach(element => element.parentNode.removeChild(element))

        result.successful.forEach(file => {
            appendUploadedFile(element, file, field_name)
            setPreview(element, file)
        })

        // this.uppy.reset();
    })
}

function appendUploadedFile(element, file, field_name) {
    const hiddenField = document.createElement('input')

    hiddenField.setAttribute('type', 'hidden')
    hiddenField.setAttribute('name', field_name)
    hiddenField.setAttribute('data-pending-upload', true)
    hiddenField.setAttribute('value', file.response.signed_id)

    element.appendChild(hiddenField)
}

function setPreview(element, file) {
    let preview = element.querySelector('[data-behavior="uppy-preview"]')
    if (preview) {
        preview.src = (file.preview) ? file.preview : "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSpj0DBTVsaja01_xWh37bcutvpd7rh7zEd528HD0d_l6A73osY"
    }
}


// Configure Stimulus development experience
application.debug = false
window.Stimulus   = application

export { application }
