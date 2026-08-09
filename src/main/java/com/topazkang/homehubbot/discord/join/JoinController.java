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

            <form method="post">
                <button type="submit">
                    접속 허용
                </button>
            </form>
        </body>
        </html>
        """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

    @PostMapping("/{token}")
    public ResponseEntity<String> processJoin(
            @PathVariable String token,
            HttpServletRequest request
    ) {
        String ip = request.getHeader("CF-Connecting-IP");

        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }

        joinService.processJoinLink(token, ip);

        String html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>HomeHub</title>
        </head>
        <body>
            <h2>접속 요청이 완료되었습니다.</h2>
            <p>이 창은 자동으로 닫힙니다.</p>
            <script>
                setTimeout(() => {
                    window.close();
                }, 1000);
            </script>
        </body>
        </html>
        """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

}
