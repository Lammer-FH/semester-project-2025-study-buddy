<template>
  <form>
    <ion-item
      :class="{ 'ion-invalid': showTitleError }"
      style="margin-bottom: 12px"
    >
      <ion-label position="stacked">Title *</ion-label>
      <ion-input v-model="localAssignment.title" type="text" required />
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
      <ion-textarea v-model="localAssignment.description" :rows="5" />
    </ion-item>

    <ion-item style="margin-bottom: 8px">
      <ion-label slot="start" style="margin-bottom: 8px">Deadline</ion-label>
      <ion-datetime-button datetime="datetime" slot="end" />
    </ion-item>

    <ion-modal
      :keep-contents-mounted="true"
      :is-open="modalOpen"
      @did-dismiss="modalOpen = false"
    >
      <ion-datetime
        id="datetime"
        v-model="localAssignment.deadline"
        presentation="date"
        :min="today"
        cancel-text="Cancel"
        done-text="Done"
        @ionCancel="modalOpen = false"
        @ionChange="onDateChange"
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
  IonDatetime,
  IonDatetimeButton,
  IonModal,
} from "@ionic/vue";
import type { Assignment } from "@/types/assignment";

export default {
  name: "AssignmentFormFields",
  components: {
    IonItem,
    IonLabel,
    IonInput,
    IonTextarea,
    IonNote,
    IonDatetime,
    IonDatetimeButton,
    IonModal,
  },
  props: {
    assignment: {
      type: Object as () => Assignment,
      required: true,
    },
    validateFields: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:assignment", "validation-change"],
  data() {
    return {
      localAssignment: {} as Assignment,
      today: new Date().toISOString().split("T")[0], // YYYY-MM-DD
      modalOpen: false,
    };
  },
  computed: {
    showTitleError(): boolean {
      return (
        this.validateFields &&
        (!this.localAssignment.title ||
          this.localAssignment.title.trim() === "")
      );
    },
    isValid(): boolean {
      return !!(
        this.localAssignment.title && this.localAssignment.title.trim()
      );
    },
  },
  watch: {
    assignment: {
      immediate: true,
      deep: true,
      handler(newVal: Assignment) {
        this.localAssignment = {
          ...newVal,
          deadline: newVal.deadline
            ? new Date(newVal.deadline).toISOString().split("T")[0]
            : new Date().toISOString().split("T")[0],
        };
      },
    },
    localAssignment: {
      handler(newValue) {
        this.$emit("update:assignment", { ...newValue });
        this.$emit("validation-change", this.isValid);
      },
      deep: true,
    },
    isValid(newValue) {
      this.$emit("validation-change", newValue);
    },
  },
  methods: {
    onDateChange() {
      this.modalOpen = false;
      // emit updated assignment on date change
      this.$emit("update:assignment", { ...this.localAssignment });
    },
  },
  mounted() {
    // listen for datetime button click to open modal
    const datetimeButton = this.$el.querySelector("ion-datetime-button");
    if (datetimeButton) {
      datetimeButton.addEventListener("click", () => {
        this.modalOpen = true;
      });
    }
  },
};
</script>
