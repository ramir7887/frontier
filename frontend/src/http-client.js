import axios from "axios";

export function httpClient() {
	const headers = {
		"Content-Type": "application/json"
	}

	return axios.create({
		headers: headers,
		baseURL: "https://frontier-api.kovalev.team"
	})
}
