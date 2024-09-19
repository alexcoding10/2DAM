use crate::jugador::Jugador;

// Define la estructura FicheroServicio
pub struct FicheroServicio {
    get_jugadores: fn() -> Vec<Jugador>,
}

// Implementa métodos para FicheroServicio
impl FicheroServicio {
    // Crea una nueva instancia de FicheroServicio
    pub fn new(get_jugadores: fn() -> Vec<Jugador>) -> Self {
        FicheroServicio { get_jugadores }
    }

    // Método para obtener jugadores usando la función almacenada en el campo
    pub fn obtener_jugadores(&self) -> Vec<Jugador> {
        (self.get_jugadores)()
    }
}

// Función que devuelve una lista de jugadores
pub fn obtener_lista_de_jugadores() -> Vec<Jugador> {
    let j1 = Jugador::new("pepe", 20);
    let j2 = Jugador::new("Manuel", 12);
    let mut lista_jugadores = Vec::new();
    lista_jugadores.push(j1);
    lista_jugadores.push(j2);
    lista_jugadores
}