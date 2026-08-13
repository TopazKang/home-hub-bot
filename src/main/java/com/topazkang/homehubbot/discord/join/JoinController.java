package com.topazkang.homehubbot.discord.join;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/{token}")
    public ResponseEntity<String> joinPage(
            @PathVariable String token
    ) {
        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Home Hub</title>
            </head>
            
            <body>
                <p id="message">게임 서버 접속을 준비하고 있습니다...</p>
            
                <script>
                    window.addEventListener("load", async () => {
                        try {
                            const response = await fetch(window.location.pathname, {
                                method: "POST"
                            });
            
                            if (response.ok) {
                                document.getElementById("message").innerText =
                                    "접속 준비가 완료되었습니다.";
            
                                setTimeout(() => {
                                    window.close();
                                }, 1000);
                            } else {
                                document.getElementById("message").innerText =
                                    "접속 준비에 실패했습니다.";
                            }
                        } catch (e) {
                            document.getElementById("message").innerText =
                                "서버와 통신할 수 없습니다.";
                        }
                    });
                </script>
            </body>
            </html>
            """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

    @PostMapping("/{token}")
    public ResponseEntity<Void> processJoin(
            @PathVariable String token,
            HttpServletRequest request
    ) {
        String ip = request.getHeader("CF-Connecting-IP");

        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }

        joinService.processJoinLink(token, ip);

        return ResponseEntity.ok().build();
    }

}
