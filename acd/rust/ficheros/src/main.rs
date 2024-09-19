// src/main.rs
use actix_web::{App, HttpServer};
mod controller; // Este debe coincidir con el nombre del directorio en `src`

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(controller::fichero_controller::hello_world) // Debe coincidir con lo que se expone en `controllers/mod.rs`
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}