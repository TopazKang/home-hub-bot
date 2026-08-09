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
                <title>HomeHub</title>
            </head>
            <body>
                <h2>게임 서버 접속</h2>
                <button onclick="join()">접속 허용</button>
                
                <p id="message"></p>

                <script>
                    async function join() {
                        const button = document.querySelector("button");
                        const message = document.getElementById("message");

                        button.disabled = true;
                        message.innerText = "접속 처리 중...";

                        try {
                            const response = await fetch(window.location.pathname, {
                                method: "POST"
                            });

                            if (!response.ok) {
                                throw new Error("JOIN 실패");
                            }

                            message.innerText = "접속 요청이 완료되었습니다.";

                            setTimeout(() => {
                                window.close();
                            }, 1000);

                        } catch (e) {
                            message.innerText = "접속 요청에 실패했습니다.";
                            button.disabled = false;
                        }
                    }
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
