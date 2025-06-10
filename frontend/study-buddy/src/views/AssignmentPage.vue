<template>
  <base-layout
    page-title="Assignment"
    :show-back-button="true"
    :default-back-link="`tabs/assignment-list`"
  >
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button fill="outline" color="primary" @click="goToCreateTask">
          <ion-icon :icon="add" slot="start" />
          Add Task
        </ion-button>
      </ion-buttons>
    </template>
    <app-spinner v-if="loading" class="loading-spinner"></app-spinner>
    <div v-if="assignmentStore.currentAssignment" class="assignment-content">
      <div class="assignment-header">
        <div class="assignment-title-wrapper">
          <h2 class="assignment-title">
            <ion-icon :icon="listOutline" class="assignment-icon" />
            {{ assignmentStore.currentAssignment.title }}
          </h2>
        </div>
        <div
          v-if="assignmentStore.currentAssignment.description"
          class="assignment-description-wrapper"
        >
          <p class="assignment-description">
            {{ assignmentStore.currentAssignment.description }}
          </p>
        </div>
      </div>
    </div>

    <!-- Tasks Section -->
    <div class="assignments-section">
      <div class="section-header">
        <h3 class="section-title">Tasks</h3>
        <div class="section-divider"></div>
      </div>

      <div class="assignments-content">
        <task-list
          v-if="tasks.length > 0"
          :tasks="tasks"
          @update-task="handleEdit"
          @delete-task="confirmDelete"
          class="assignment-list"
        />
        <div v-else-if="!loading" class="empty-state">
          <div class="empty-state-content">
            <ion-icon :icon="terminalOutline" class="empty-icon"></ion-icon>
            <p class="empty-message">No tasks found for this assignment.</p>
            <p class="empty-submessage">
              Get started by creating your first task.
            </p>
          </div>
        </div>
      </div>
    </div>
  </base-layout>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import { useAssignmentStore } from "@/stores/assignmentStore";
import { useTaskStore } from "@/stores/taskStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import TaskList from "@/components/organisms/TaskList.vue";
import { listOutline, terminalOutline, add } from "ionicons/icons";
import { IonButton, IonButtons, IonIcon } from "@ionic/vue";

export default defineComponent({
  name: "AssignmentPage",
  components: {
    TaskList,
    AppSpinner,
    IonButton,
    IonButtons,
    IonIcon,
  },
  data() {
    return {
      assignmentStore: useAssignmentStore(),
      taskStore: useTaskStore(),
      listOutline,
      add,
      terminalOutline,
      taskIdToDelete: null as number | null,
      taskDescriptionToDelete: "",
      showDialog: false,
      error: "",
    };
  },
  computed: {
    tasks() {
      return this.taskStore.list;
    },
    loading() {
      return this.taskStore.isLoading;
    },
    assignmentId(): number {
      return Number(this.$route.params.id);
    },
  },
  async mounted() {
    await this.loadTaskData();
  },
  watch: {
    "$route.params.id"() {
      // Only load course data if we're actually on a course page
      if (this.$route.name === "AssignmentPage") {
        this.loadTaskData();
      }
    },
  },
  methods: {
    async loadTaskData() {
      const id = this.getValidAssignmentId();
      if (id === null) {
        console.log("No assignment id found in route params");
        this.$router.replace("/tabs/assignment-list");
        return;
      }
      // await this.assignmentStore.selectAssignment(id);
      this.assignmentStore.getAssignment(id);
      if (this.assignmentStore.currentAssignment) {
        console.log(
          "Set current assignmment to ",
          this.assignmentStore.currentAssignment
        );
        await this.taskStore.fetchByAssignmentId(id);
      }
    },
    getValidAssignmentId(): number | null {
      const id = Number(this.$route.params.id);
      return isNaN(id) ? null : id;
    },
    handleEdit() {
      console.log("Editing task");
    },
    confirmDelete(taskId: number) {
      console.log("deleting task", taskId);
    },
    goToCreateTask() {
      console.log("go to create task");
    },
  },
});
</script>

<style scoped>
.assignment-icon {
  margin-right: 12px;
  font-size: 1.2em;
  vertical-align: middle;
}
.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}

.assignment-content {
  background: var(--ion-color-light);
  margin: 16px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.assignment-header {
  padding: 24px;
}

.assignment-title-wrapper {
  margin-bottom: 12px;
}

.assignment-title {
  margin: 0;
  font-size: 1.75em;
  font-weight: 600;
  line-height: 1.3;
  color: var(--ion-color-dark);
}

.assignment-description-wrapper {
  background: transparent;
  padding: 0;
  border-radius: 0;
  margin-top: 16px;
}

.assignment-description {
  margin: 0;
  font-size: 1em;
  line-height: 1.6;
  color: var(--ion-color-medium-shade);
}

.assignments-section {
  margin: 16px;
}

.section-header {
  margin-bottom: 20px;
}

.section-title {
  margin: 0 0 8px 0;
  font-size: 1.5em;
  font-weight: 700;
  color: var(--ion-color-dark);
}

.section-divider {
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--ion-color-primary) 0%,
    var(--ion-color-primary-tint) 100%
  );
  border-radius: 2px;
  width: 60px;
}

.assignments-content {
  background: var(--ion-color-light);
  border-radius: 12px;
  overflow: hidden;
}

.assignment-list {
  background: transparent;
}

.empty-state {
  padding: 48px 24px;
  text-align: center;
}

.empty-state-content {
  max-width: 280px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 4em;
  color: var(--ion-color-medium);
  margin-bottom: 16px;
  opacity: 0.6;
}

.empty-message {
  margin: 0 0 8px 0;
  font-size: 1.1em;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.empty-submessage {
  margin: 0;
  font-size: 0.9em;
  color: var(--ion-color-medium-shade);
  line-height: 1.4;
}

/* Dark mode support */
@media (prefers-color-scheme: dark) {
  .assignment-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .assignment-title {
    color: var(--ion-color-light);
  }

  .assignment-description {
    color: var(--ion-color-step-600);
  }

  .assignments-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .section-title {
    color: var(--ion-color-light);
  }

  .empty-message {
    color: var(--ion-color-light);
  }
}

/* Responsive design */
@media (max-width: 768px) {
  .assignment-content {
    margin: 8px;
  }

  .assignments-section {
    margin: 8px;
  }

  .assignment-header {
    padding: 20px;
  }

  .assignment-title {
    font-size: 1.5em;
  }
}
</style>
