<template>
    <div class="analyze">
        <v-text-field v-model="search" label="Поиск"></v-text-field>

        <div class="filters">
            <v-autocomplete
                label="Маркеры"
                style="width: 350px"
                :items="filters.markers"
                v-model="marker"
            />
            <v-autocomplete class="mx-2" label="Источники" style="width: 350px" 
                :items="filters.sources"
                v-model="source"
            />
            <DatePickerInputComponent
                v-model="filters.startDate"
                placeholder="Дата начала"
                style="width: 350px"
            />
            <DatePickerInputComponent
                v-model="filters.endDate"
                placeholder="Дата конца"
                style="width: 350px"
            />
        </div>

        <div class="buttons">
            <v-btn
                color="primary"
                depressed
                class="my-3"
                :disabled="!selectedSubjects.length"
                @click="goToChartsPage"
            >
                Перейти к графикам
            </v-btn>
        </div>

        <SubjectCard
            style="margin-bottom: 20px"
            v-for="s in filterSearch"
            :key="s.id"
            :subject="s"
            :selected="s.selected"
            @toggleSelect="s.selected = !s.selected"
        />
    </div>
</template>

<script>
import DatePickerInputComponent from "../components/DatePickerInputComponent";
import SubjectCard from "../components/SubjectCard";
import { httpClient } from "../http-client";

export default {
    name: "AnalyzePage",
    components: { SubjectCard, DatePickerInputComponent },
    props: {
        source: {
            type: String,
            required: false,
            default: "Все"
        }
    },
    data: () => ({
        search: "",
        subjects: [],
        branchIds: [],
        filters: {
            markers: [],
            sources: [],
            startDate: null,
            endDate: null
        },
        branches: [],
        marker: "Все"
    }),
    created() {
        this.branchIds = this.$route.query.branches?.split(",").map(Number);
    },
    async mounted() {
        //this.subjects = (await httpClient().post("/subject/branch", this.branchIds)).data;
        const subjects = (await httpClient().post("/subject/branch", this.branchIds)).data;
        this.branches = (await httpClient().get("/branch")).data;
        for (let i = 0; i < subjects.length; i++) {
            if (
                this.branches.filter(branch => branch.branchScience.id == subjects[i].branchId)[0]
            ) {
                subjects[i]["branch_name"] = this.branches.filter(
                    branch => branch.branchScience.id == subjects[i].branchId
                )[0];
            } else {
                subjects[i]["branch_name"] = "";
            }
            this.subjects = subjects.map(b => ({
                ...b,
                selected: false
            }));
        }
        // маркеры
        const markers = (await httpClient().get("/marker")).data;
        this.filters.markers = markers.map(marker => marker.name);
        this.filters.markers.push("Все");
        // источники
        const sources = (await httpClient().get("/source")).data;
        this.filters.sources = sources.map(source => source.name);
        this.filters.sources.push("Все");
    },
    computed: {
        filterSearch() {
            if (this.source == "McKinsey") {
                if (this.search == "") {
                    return this.subjects.filter(subject => subject.isMckinsey == "1");
                } 
                if (this.search != "") {
                    return this.subjects.filter(subject => subject.isMckinsey == "1" && subject.name.toLowerCase().includes(this.search.toLowerCase()));
                }
                return this.subjects.filter(subject => subject.isMckinsey == "1");
            }
            if (this.source == "Gartner") {
                if (this.search == "") {
                    return this.subjects.filter(subject => subject.isGartner == "1");
                } 
                if (this.search != "") {
                    return this.subjects.filter(subject => subject.isGartner == "1" && subject.name.toLowerCase().includes(this.search.toLowerCase()));
                }
                return this.subjects.filter(subject => subject.isGartner == "1");
            }
            if (this.search == "") {
                if (this.marker == "Фронтир") {
                    if (this.source == "McKinsey") {
                        return this.subjects.filter(subject => subject.isFrontir == "1" && subject.isMckinsey == "1");
                    }
                    if (this.source == "Gartner") {
                        return this.subjects.filter(subject => subject.isFrontir == "1" && subject.isGartner == "1");
                    }
                    return this.subjects.filter(subject => subject.isFrontir == "1");
                }
                return this.subjects;
            } else {
                if (this.marker == "Фронтир") {
                    if (this.source == "McKinsey") {
                        return this.subjects.filter(subject => subject.isFrontir== "1" 
                                                    && subject.name.toLowerCase().includes(this.search.toLowerCase()) 
                                                    && subject.isMckinsey == "1"
                                                    );
                    }
                    if (this.source == "Gartner") {
                         return this.subjects.filter(subject => subject.isFrontir == "1"
                                                    && subject.name.toLowerCase().includes(this.search.toLowerCase()) 
                                                    && subject.isGartner == "1"
                                                    );
                    }
                }
                if (this.source == "McKinsey") {
                    return this.subjects.filter(subject => subject.name.toLowerCase().includes(this.search.toLowerCase()) 
                                                && subject.isMckinsey == "1"
                                                );
                }
                if (this.source == "Gartner") {
                    return this.subjects.filter(subject => subject.name.toLowerCase().includes(this.search.toLowerCase()) 
                                                && subject.isGartner == "1"
                                                );

                }
                return this.subjects.filter(subject =>
                    subject.name.toLowerCase().includes(this.search.toLowerCase())
                );
            }
        },
        selectedSubjects() {
            return this.subjects.filter(s => s.selected);
        }
    },
    methods: {
        goToChartsPage() {
        	const selectedSubjectIds = this.selectedSubjects.map(s => s.name).join(",");

            this.$router.push({
                path: "/charts",
                query: {
                	subjects: selectedSubjectIds
				}
            });
        }
    }
};
</script>

<style scoped lang="scss">
.filters {
    display: flex;
}
</style>
