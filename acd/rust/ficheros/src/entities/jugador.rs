// definir estructura del jugador
pub struct Jugador {
    name: String,
    edad: u32,
}

// implementa metodos para el jugador
impl jugador {
    // constructor
    pub fn new(name: String, edad: u32) -> self {
        jugador { name, edad }
    }

    // Método para obtener el nombre
    pub fn get_name(&self) -> &str {
        &self.name
    }

    // Método para establecer el nombre
    pub fn set_name(&mut self, name: String) {
        self.name = name;
    }

    // Método para obtener la edad
    pub fn get_edad(&self) -> u32 {
        self.edad
    }

    // Método para establecer la edad
    pub fn set_edad(&mut self, edad: u32) {
        self.edad = edad;
    }
}
