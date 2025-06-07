<template>
  <div v-if="localAssignment">
    <assignment-form-fields
      :assignment="localAssignment"
      :validate-fields="hasTriedSubmit"
      @update:assignment="updateLocal"
      @validation-change="handleValidationChange"
    />
    <ion-button expand="block" @click="submit">
      {{ buttonLabel }}
    </ion-button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentFormFields from "@/components/molecules/AssignmentFormFields.vue";
import type { Assignment } from "@/types/assignment";
import { IonButton } from "@ionic/vue";

export default defineComponent({
  name: "AssignmentFormWrapper",
  components: { AssignmentFormFields, IonButton },
  props: {
    initialAssignment: {
      type: Object as () => Partial<Assignment>,
      required: true,
    },
    buttonLabel: { type: String, default: "Submit" },
  },
  emits: ["submit"],
  data() {
    return {
      localAssignment: { ...this.initialAssignment } as Assignment,
      hasTriedSubmit: false,
      isFormValid: false,
    };
  },
  methods: {
    updateLocal(updated: Assignment) {
      this.localAssignment = updated;
    },
    handleValidationChange(isValid: boolean) {
      this.isFormValid = isValid;
    },
    submit() {
      this.hasTriedSubmit = true;

      if (this.isFormValid) {
        this.$emit("submit", { ...this.localAssignment });
      }
    },
  },
});
</script>
