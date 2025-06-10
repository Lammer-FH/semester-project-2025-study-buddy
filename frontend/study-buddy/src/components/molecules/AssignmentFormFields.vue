<template>
  <form>
    <ion-item
      :class="{ 'ion-invalid': showTitleError }"
      style="margin-bottom: 12px"
    >
      <ion-label position="stacked">Title *</ion-label>
      <ion-input v-model="form.title" type="text" required />
    </ion-item>
    <ion-note
      v-if="showTitleError"
      color="danger"
      style="padding-left: 16px; padding-top: 4px; margin-bottom: 16px"
    >
      Title is required
    </ion-note>

    <ion-item style="margin-bottom: 16px">
      <ion-label position="stacked" style="margin-bottom: 8px"
        >Description</ion-label
      >
      <ion-textarea v-model="form.description" :rows="5" />
    </ion-item>

    <ion-item style="margin-bottom: 8px">
      <ion-label slot="start" style="margin-bottom: 8px">Deadline</ion-label>
      <ion-item slot="end">
        <ion-icon :icon="calendar" size="large" fill="outline" color="primary">
        </ion-icon>
        <ion-datetime-button
          datetime="datetime"
          slot="end"
          class="custon-datetime-button"
        />
      </ion-item>
    </ion-item>

    <ion-modal :keep-contents-mounted="true">
      <ion-datetime
        id="datetime"
        v-model="form.deadline"
        presentation="date"
        :min="today"
        :show-default-buttons="true"
        class="custom-datetime"
      />
    </ion-modal>
  </form>
</template>

<script lang="ts">
import {
  IonItem,
  IonLabel,
  IonInput,
  IonTextarea,
  IonNote,
  IonDatetimeButton,
  IonDatetime,
  IonModal,
  IonIcon,
} from "@ionic/vue";
import type { Assignment } from "@/types/assignment";
import { calendar } from "ionicons/icons";

export default {
  name: "AssignmentFormFields",
  components: {
    IonItem,
    IonLabel,
    IonInput,
    IonTextarea,
    IonNote,
    IonDatetimeButton,
    IonDatetime,
    IonModal,
    IonIcon,
  },
  props: {
    assignment: {
      type: Object as () => Assignment | null,
      default: null,
    },
    validateFields: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["form-change", "validation-change"],
  data() {
    return {
      form: {
        id: this.assignment?.id || 0,
        title: this.assignment?.title || "",
        description: this.assignment?.description || "",
        deadline:
          this.assignment?.deadline || new Date().toISOString().split("T")[0],
      },
      calendar,
      today: new Date().toISOString().split("T")[0],
    };
  },
  computed: {
    showTitleError(): boolean {
      return (
        this.validateFields &&
        (!this.form.title || this.form.title.trim() === "")
      );
    },
    isValid(): boolean {
      return !!(this.form.title && this.form.title.trim());
    },
  },
  watch: {
    form: {
      handler() {
        console.log("Form updated", this.form);
        this.$emit("form-change", this.form);
      },
      deep: true,
    },
    isValid: {
      handler(newValue) {
        this.$emit("validation-change", newValue);
      },
      immediate: true,
    },
  },
  methods: {
    getFormData() {
      return { ...this.form };
    },
    resetForm() {
      this.form = {
        id: this.assignment?.id || 0,
        title: this.assignment?.title || "",
        description: this.assignment?.description || "",
        deadline:
          this.assignment?.deadline || new Date().toISOString().split("T")[0],
      };
    },
  },
};
</script>

<style scoped>
/* For the modal container */
ion-modal {
  --border-radius: 16px;
  --box-shadow: 0 10px 15px -3px rgba(var(--ion-color-primary-rgb), 0.3);
}

/* For the datetime picker itself */
.custom-datetime {
  --background: var(--ion-color-light);
  border-radius: 16px;
  overflow: hidden;
  margin: 16px;

  /* Calendar header styling */
  &::part(calendar-header) {
    background: var(--ion-color-primary);
    color: white;
    border-top-left-radius: 16px;
    border-top-right-radius: 16px;
  }

  /* Date grid styling */
  &::part(calendar-day) {
    border-radius: 50%;
  }

  &::part(calendar-day active) {
    background: var(--ion-color-primary);
    color: white;
  }

  /* Button styling */
  &::part(buttons) {
    padding: 16px;
    background: var(--ion-color-light-shade);
    border-bottom-left-radius: 16px;
    border-bottom-right-radius: 16px;
  }

  /* Today's date highlight */
  &::part(today) {
    color: var(--ion-color-primary);
    font-weight: bold;
  }
}

/* Style the datetime button that triggers the modal */
ion-datetime-button::part(native) {
  background: var(--ion-color-light);
  border-radius: 8px;
  padding: 8px 12px;
}

/* Style the button text */
ion-datetime-button::part(text) {
  color: var(--ion-color-primary);
  font-weight: 500;
}
</style>
