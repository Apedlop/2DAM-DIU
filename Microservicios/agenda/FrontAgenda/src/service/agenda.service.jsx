import http from "../http-common";

class AgendaDataService {
    getAll() {
        return http.get("/agenda");
    }

    get(id) {
        return http.get(`/agenda/${id}`);
    }

    create(data) {
        return http.post("/agenda", data);
    }

    update(id, data) {
        return http.put(`/agenda/${id}`, data);
    }

    delete(id) {    
        return http.delete(`/agenda/${id}`);
    }

    deleteAll() {    
        return http.delete("/agenda");
    }

    findByName(name) {
        return http.get(`/agenda/nombre/${name}`);
    }
}

export default new AgendaDataService();