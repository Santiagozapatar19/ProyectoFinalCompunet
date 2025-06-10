# Proyecto Final Computación en Internet 1 (Numeros Perfectos)

**Requisitos previos**  
- JDK 17 instalado 
- ICE 3.7.10 instalado y en tu `PATH` (para `slice2java`).   

---

## 1. Limpieza y compilación (por máquina)

> **Nota:** En **cada** equipo (servidor, workers y cliente) debes ejecutar estos comandos **antes** de arrancar cualquier componente.

```bash
# Desde la raíz del proyecto
./gradlew clean
./gradlew build
```

## 2. Ejecutar el servidor maestro (Equipo 1)
```bash
./gradlew :server:run 
```

## 3. Verificar conectividad (desde otros equipos)
```bash
ping 192.168.131.39
telnet 192.168.131.39 10000
```

## 4. Ejecutar workers (Desde terminales)
```bash
./gradlew :worker:run
```

## 5. Ejecutar el cliente con rango numérico especifico
```bash
./gradlew :client:run --args="1 10000"
```


## Notas Adicionales
>Estas instrucciones asumen que: 
> - Estás en el directorio raíz del proyecto
> - Tienes permisos de ejecución
> - La red permite comunicación en el puerto 10000
> - Todos los equipos tienen la misma versión de JDK (17)

