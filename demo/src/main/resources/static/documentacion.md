
### 

## UserController



1. **Obtener usuario por nombre de usuario**
   - **Método:** GET
   - **URL:** `/api/{username}`
   - **Descripción:** Obtiene la información de un usuario por su nombre de usuario.
   - **Ejemplo de URL:** 

http://localhost:8080/api/johndoe



2. **Eliminar usuario por nombre de usuario**
   - **Método:** DELETE
   - **URL:** `/api/{username}`
   - **Descripción:** Elimina un usuario por su nombre de usuario.
   - **Ejemplo de URL:** 

http://localhost:8080/api/johndoe



### 

## MusicArtistUserController



1. **Crear un usuario artista**
   - **Método:** POST
   - **URL:** `/api/artist`
   - **Descripción:** Crea un nuevo usuario artista.
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "username": "artistname",
       "password": "password123"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/artist



### 

## MusicEnthusiastUserController



1. **Crear un usuario entusiasta de la música**
   - **Método:** POST
   - **URL:** `/api/enthusiast`
   - **Descripción:** Crea un nuevo usuario entusiasta de la música.
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "username": "enthusiastname",
       "password": "password123"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/enthusiast



### 

## PlayListController



1. **Obtener todas las listas de reproducción**
   - **Método:** GET
   - **URL:** `/api/playlist/playlists`
   - **Descripción:** Obtiene todas las listas de reproducción con el conteo de canciones.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/playlists



2. **Obtener una lista de reproducción por ID**
   - **Método:** GET
   - **URL:** `/api/playlist/{id}`
   - **Descripción:** Obtiene una lista de reproducción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/1



3. **Crear una lista de reproducción**
   - **Método:** POST
   - **URL:** `/api/playlist/playlist`
   - **Descripción:** Crea una nueva lista de reproducción.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "name": "My Playlist"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/playlist



4. **Actualizar el nombre de una lista de reproducción**
   - **Método:** PUT
   - **URL:** `/api/playlist/{id}`
   - **Descripción:** Actualiza el nombre de una lista de reproducción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "name": "Updated Playlist Name"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/1



5. **Eliminar una lista de reproducción**
   - **Método:** DELETE
   - **URL:** `/api/playlist/{id}`
   - **Descripción:** Elimina una lista de reproducción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/1



6. **Agregar una canción a una lista de reproducción**
   - **Método:** POST
   - **URL:** `/api/playlist/{id}/song`
   - **Descripción:** Agrega una canción a una lista de reproducción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "songID": 1
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/1/song



7. **Obtener mis listas de reproducción**
   - **Método:** GET
   - **URL:** `/api/playlist/me/playlists`
   - **Descripción:** Obtiene las listas de reproducción del usuario autenticado.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/playlist/me/playlists



### 

## SongController



1. **Obtener canciones**
   - **Método:** GET
   - **URL:** `/api/song/songs`
   - **Descripción:** Obtiene canciones filtradas por artista y/o género.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Parámetros de consulta opcionales:** artist,genre


   - **Ejemplo de URL:** 

http://localhost:8080/api/song/songs?artist=artistname&genre=ROCK



2. **Obtener una canción por ID**
   - **Método:** GET
   - **URL:** `/api/song/{id}`
   - **Descripción:** Obtiene una canción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/song/1



3. **Crear una canción**
   - **Método:** POST
   - **URL:** `/api/song`
   - **Descripción:** Crea una nueva canción.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "name": "New Song",
       "genre": "ROCK",
       "artist": "artistname"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/song



4. **Actualizar una canción**
   - **Método:** PUT
   - **URL:** `/api/song/{id}`
   - **Descripción:** Actualiza una canción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Cuerpo de la solicitud (JSON):**
     ```json
     {
       "name": "Updated Song",
       "genre": "POP",
       "artist": "artistname"
     }
     ```
   - **Ejemplo de URL:** 

http://localhost:8080/api/song/1



5. **Eliminar una canción**
   - **Método:** DELETE
   - **URL:** `/api/song/{id}`
   - **Descripción:** Elimina una canción por su ID.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/song/1



6. **Obtener mis canciones**
   - **Método:** GET
   - **URL:** `/api/song/me-songs`
   - **Descripción:** Obtiene las canciones del usuario autenticado.
   - **Encabezado:** `Authorization: Bearer {token}`
   - **Ejemplo de URL:** 

http://localhost:8080/api/song/me-songs


