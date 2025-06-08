import secrets
import base64
import hashlib
import hmac

def generate_jwt_secret():
    """
    Genera una chiave segreta sicura per JWT con HMAC-SHA256
    """
    # Genera 32 bytes casuali (256 bit) per HMAC-SHA256
    secret_bytes = secrets.token_bytes(32)
    
    # Codifica in Base64 per l'uso nelle configurazioni
    base64_secret = base64.b64encode(secret_bytes).decode('utf-8')
    
    print("=== CHIAVE JWT GENERATA ===")
    print("Chiave Base64: {base64_secret}")
    print("Lunghezza: {len(secret_bytes)} bytes ({len(secret_bytes) * 8} bit)")
    print("\nAggiungi questa chiave al tuo application.properties:")
    print("jwt.secret={base64_secret}")
    print("jwt.expiration-ms=86400000  # 24 ore in millisecondi")
    
    return base64_secret

def generate_multiple_keys(count=3):
    """
    Genera più chiavi per scegliere
    """
    print("=== GENERAZIONE DI {count} CHIAVI JWT ===\n")
    
    for i in range(1, count + 1):
        secret_bytes = secrets.token_bytes(32)
        base64_secret = base64.b64encode(secret_bytes).decode('utf-8')
        print(f"Chiave {i}: {base64_secret}")
    
    print("\nScegli una delle chiavi sopra per il tuo jwt.secret")

if __name__ == "__main__":
    # Genera una chiave singola
    generate_jwt_secret()
    
    print("\n" + "="*50 + "\n")
    
    # Genera 3 chiavi tra cui scegliere
    generate_multiple_keys(3)