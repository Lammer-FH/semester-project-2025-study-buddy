<template>
  <ion-alert
    :is-open="visible"
    @did-dismiss="handleCancel"
    :header="title"
    :message="message"
    :buttons="alertButtons"
    class="custom-alert"
  />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonAlert } from "@ionic/vue";

export default defineComponent({
  name: "ConfirmDialog",
  components: {
    IonAlert,
  },
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
    title: {
      type: String,
      default: "Confirm",
    },
    message: {
      type: String,
      required: true,
    },
  },
  emits: ["confirm", "cancel"],
  computed: {
    alertButtons(): Array<{
      text: string;
      role?: string;
      handler: () => void;
    }> {
      return [
        {
          text: "Cancel",
          role: "cancel",
          handler: this.handleCancel,
        },
        {
          text: "Yes",
          handler: this.handleConfirm,
        },
      ];
    },
  },
  methods: {
    handleConfirm() {
      this.$emit("confirm");
    },
    handleCancel() {
      this.$emit("cancel");
    },
  },
});
</script>

<style scoped>
.custom-alert {
  --width: 90%;
  --max-width: 400px;
  --border-radius: 8px;
  --background: var(--ion-color-light);
  --box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}
</style>
