
## 🚀 Reactive Programming in Spring (Summary)

### 🎯 What is Reactive Programming?

Reactive programming is a programming paradigm focused on **building asynchronous, non-blocking, event-driven systems** that can **handle backpressure** and **scale efficiently**.

Instead of *waiting* for data (blocking), you *react* to it as it becomes available.

---

### ⚠️ Why was it needed?

Traditional Java apps (Spring MVC) use a **thread-per-request** model:

- One request = One thread (even if just waiting on DB/API)
- Threads are heavy: more memory, more context-switching
- Doesn’t scale well under high load

**Reactive solves this by:**

- Avoiding thread blocking
- Using **event loops** + **non-blocking I/O**
- Letting a small number of threads serve many requests

---

### 🌊 Core Concepts: Mono & Flux

| Type    | Description                 | Analogy                   |
|---------|-----------------------------|----------------------------|
| `Mono<T>` | Emits **0 or 1 item**         | A single package 📦         |
| `Flux<T>` | Emits **0 to many items**     | A stream of packages 📦📦📦 |

Reactive types are **lazy**: they only execute when you call `.subscribe()`.

---

### 🔗 Chaining Operations

Reactive APIs allow fluent chaining of operations:

```java
Mono.just("hello")
    .map(String::toUpperCase)
    .flatMap(this::callExternalService)
    .subscribe(System.out::println);
```

---

### 🚨 Error Handling Strategies

Reactive streams handle errors with built-in operators that let you recover or fallback cleanly:

#### ✅ `onErrorResume()`
Gracefully fallback with another reactive type:
```java
Mono.just("start")
    .flatMap(this::unstableCall)
    .onErrorResume(error -> {
        log.error("Recovering from error: ", error);
        return Mono.just("fallback value");
    });
```

#### ✅ `onErrorReturn()`
Use a static value instead of failing:
```java
Mono.just("start")
    .flatMap(this::unstableCall)
    .onErrorReturn("fallback static");
```

#### ✅ `retry(n)`
Automatically retry `n` times before giving up:
```java
Mono.fromCallable(this::mightFail)
    .retry(3);
```

#### ✅ `doOnError()`
Log or perform side effects (but doesn’t prevent the error):
```java
Mono.just("start")
    .flatMap(this::unstableCall)
    .doOnError(e -> log.error("Something went wrong!", e));
```

#### ✅ `onErrorMap()`
Transform the exception into another type:
```java
Mono.just("data")
    .flatMap(this::unstableCall)
    .onErrorMap(original -> new CustomException("Wrapped error", original));
```

---

### ✅ Benefits

- Scalable under load (fewer threads)
- Efficient memory usage
- Natural fit for event-driven or streaming applications
- Fine-grained error control

# 🌪️ What Is Reactive Programming?

## 🧵 Traditional Java Thread Model

- Each task (e.g., HTTP request) gets its own thread.
- Threads are expensive: memory-heavy and cause CPU context-switching.
- Blocking I/O (e.g., HTTP, DB) means threads just wait, wasting resources.
- Doesn't scale well under high load (e.g., 10,000 concurrent users).

### ❌ Problems:
- Memory bloat due to too many threads.
- Poor CPU utilization from idle waiting.
- Out-of-memory and poor performance at scale.

---

## ⚡ Reactive Programming: The Fix

- **Non-blocking, asynchronous** execution model.
- Uses **event loops** + **callbacks** (not blocking threads).
- Small number of threads handles **many concurrent tasks**.
- Powered by libraries like **Project Reactor (Mono, Flux)** and **Spring WebFlux**.

### ✅ Benefits:
- Great scalability with low resource usage.
- Threads don’t block on I/O, just get notified when work is ready.
- Better performance in microservices and cloud-native environments.

---

## 🎭 Analogy

- **Traditional**: One waiter per customer, standing idle until food arrives.
- **Reactive**: One waiter handles many tables. Places order, moves on, and returns only when food is ready.

