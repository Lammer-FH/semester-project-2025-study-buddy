import { alertController } from "@ionic/vue";

// Shows a confirmation alert with customizable options
export const showConfirmationAlert = async (options: {
  header: string;
  message: string;
  confirmText?: string;
  cancelText?: string;
  onConfirm: () => void;
}) => {
  const alert = await alertController.create({
    header: options.header,
    message: options.message,
    buttons: [
      {
        text: options.cancelText || "Cancel",
        role: "cancel",
      },
      {
        text: options.confirmText || "Confirm",
        role: "destructive",
        handler: options.onConfirm,
      },
    ],
  });
  await alert.present();
};

// Generic alert with a single OK button
export const showAlert = async (options: {
  header?: string;
  message: string;
  buttonText?: string;
}) => {
  const alert = await alertController.create({
    header: options.header,
    message: options.message,
    buttons: [options.buttonText || "OK"],
  });
  await alert.present();
};

// Default export for convenience
export default {
  confirm: showConfirmationAlert,
  show: showAlert,
};
