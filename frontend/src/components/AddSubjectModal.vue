<template>
    <v-dialog :value="isOpen" persistent width="600">
        <v-card>
            <v-card-title class="text-h5">
                Добавление технологии
            </v-card-title>

            <v-card-text>
                <v-text-field
                    label="Название"
                    v-model="subject.name"
                    :disabled="isFormSubmitting"
                />

                <v-autocomplete
                    label="Область науки"
                    :items="branchSelectOptions"
                    v-model="subject.branchId"
                    :disabled="isFormSubmitting"
                />

                <v-autocomplete
                    label="Методика"
                    :items="$options.methodologySelectOptions"
                    v-model="subject.methodologyId"
                    :disabled="isFormSubmitting"
                />

                <v-text-field
                    label="Инвесторы"
                    v-model="subject.investors"
                    :disabled="isFormSubmitting"
                />

                <v-text-field
                    label="Количество денег"
                    v-model="subject.marketSize"
                    :disabled="isFormSubmitting"
                />

                <v-text-field
                    label="Год статистики"
                    v-model="subject.yearStat"
                    :disabled="isFormSubmitting"
                />

                <v-checkbox
                    label="Является фронтиром"
                    v-model="subject.isFrontir"
                    :disabled="isFormSubmitting"
                />
            </v-card-text>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" outlined @click="close" :disabled="isFormSubmitting">
                    Отмена
                </v-btn>
                <v-btn
                    color="primary"
                    @click="createSubject"
                    :disabled="isFormSubmitting"
                    :loading="isFormSubmitting"
                >
                    Создать
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import { httpClient } from "../http-client";
import { showNotification } from "../utils/notification-helper";

const methodologyConstants = {
    isMckinsey: 1,
    isGartner: 2
};

const createEmptySubject = () => ({
    branchId: null,
    name: null,
    methodologyId: null,
    isFrontir: false,
    isMckinsey: false,
    isGartner: false,
    investors: null,
    yearStat: null,
    marketSize: null
});

const toStringBoolean = value => (value ? "1" : "0");

export default {
    name: "AddSubjectModal",
    data: () => ({
        isOpen: false,
        branchSelectOptions: [],
        subject: createEmptySubject(),
        isFormSubmitting: false
    }),
    methodologySelectOptions: [
        { text: "McKinsey", value: methodologyConstants.isMckinsey },
        { text: "Gartner", value: methodologyConstants.isGartner }
    ],
    async mounted() {
        const branches = (await httpClient().get("/branch")).data.map(d => d.branchScience);
        this.branchSelectOptions = branches.map(b => ({
            text: b.name,
            value: b.id
        }));
    },
    methods: {
        /*== Public methods ==*/
        open() {
            this.isOpen = true;
        },

        close() {
            this.subject = createEmptySubject();
            this.isOpen = false;
        },
        /*== Public methods ==*/
        async createSubject() {
            this.subject.isMckinsey = toStringBoolean(
                this.subject.methodologyId === methodologyConstants.isMckinsey
            );
            this.subject.isGartner = toStringBoolean(
                this.subject.methodologyId === methodologyConstants.isGartner
            );
            this.subject.isFrontir = toStringBoolean(this.subject.isFrontir);

            this.isFormSubmitting = true;

            try {
                await httpClient().post("/subject/branch/create", this.subject);
                this.close();
                showNotification("Технология успешно создана");
            } catch (e) {
                alert("Error");
                console.error(e);
            } finally {
                this.isFormSubmitting = false;
            }
        }
    }
};
</script>

<style scoped></style>
